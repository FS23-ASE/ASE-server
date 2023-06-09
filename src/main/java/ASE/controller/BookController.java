package ASE.controller;

import ASE.entity.Book;

import ASE.rest.dto.BookGetDTO;
import ASE.rest.dto.BookPostDTO;

import ASE.rest.mapper.DTOMapper;
import ASE.service.BookService;
import com.amazonaws.client.builder.AwsClientBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;



@RestController
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }
    private final Logger log = LoggerFactory.getLogger(BookService.class);

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BookGetDTO> getAllBooks() {
        // fetch all books in the internal representation
        List<Book> books = bookService.getBooks();
        List<BookGetDTO> bookGetDTOs = new ArrayList<>();

        // convert each book to the API representation
        for (Book book : books) {
            bookGetDTOs.add(DTOMapper.INSTANCE.convertEntityToBookGetDTO(book));
        }
        return bookGetDTOs;
    }


    @GetMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BookGetDTO getBook(@PathVariable("id") long id) {
        Book book = bookService.getBookbyid(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find book!");
        }
        return DTOMapper.INSTANCE.convertEntityToBookGetDTO(book);
    }


    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BookGetDTO createBook(@RequestBody BookPostDTO bookPostDTO) throws SQLException, IOException {
        // convert API book to internal representation
        Book bookInput = DTOMapper.INSTANCE.convertBookPostDTOtoEntity(bookPostDTO);
        log.debug("Created Information for: {}", bookInput);
        Book createdBook = bookService.createBook(bookInput);
        // convert internal representation of book back to API
        return DTOMapper.INSTANCE.convertEntityToBookGetDTO(createdBook);
    }


    @GetMapping("/books/seller/{sellerid}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BookGetDTO> getBookBySeller(@PathVariable("sellerid") long seller_id) {
        List<Book> books = bookService.getBookBySeller(seller_id);
        List<BookGetDTO> bookGetDTOs = new ArrayList<>();

        for (Book book : books) {
            bookGetDTOs.add(DTOMapper.INSTANCE.convertEntityToBookGetDTO(book));
        }
        return bookGetDTOs;
    }


    @PutMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateBook(@PathVariable Long id, @RequestParam(required = false) MultipartFile image) throws IOException {
        Book bookToBeUpdated = bookService.getBookbyid(id);
        Book updateBookInfo = new Book();
        if(bookToBeUpdated == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user!");
        }
        if (image != null) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            String uploadDir = "book-photos/" + id;
            File uploadDirPath = new File(uploadDir);
            if (!uploadDirPath.exists()) {
                uploadDirPath.mkdirs();
            }

            String bucketName = "images";
            String region = "us-east-1";

            boolean isS3Available;
            AmazonS3 s3Client=null;
            try {
                String s3Endpoint = "http://host.docker.internal:4566";
                s3Client = AmazonS3ClientBuilder.standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Endpoint, region))
                        .withPathStyleAccessEnabled(true)
                        .build();

                isS3Available = s3Client.doesBucketExistV2(bucketName);
            } catch (SdkClientException e) {
                isS3Available = false;
            }
            if (isS3Available) {

                try {
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(image.getSize());
                    metadata.setContentType(image.getContentType());
                    s3Client.putObject(new PutObjectRequest(bucketName, id.toString(), image.getInputStream(), metadata));
                    updateBookInfo.setImage(id.toString());
                    log.info("Successfully uploaded to S3!");

                }
                catch (SdkClientException | IOException e) {
                    log.error(e.getMessage());
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload book image to S3 Bucket");
                }
            }
            else{
                if (!uploadDirPath.exists()) {
                    uploadDirPath.mkdirs();
                }
                Path path = Paths.get(uploadDir + "/" + fileName);
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                updateBookInfo.setImage(fileName);
            }
        }

        bookService.update(bookToBeUpdated,updateBookInfo);
    }

    @GetMapping("/books/{id}/image")
    @ResponseBody
    public ResponseEntity<Resource> getUserProfileImage(@PathVariable Long id) throws IOException {
        Book book = bookService.getBookbyid(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find book!");
        }

        String fileName = book.getImage();
        if (fileName == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book has no book image!");
        }

        String region = "us-east-1";
        boolean isS3Available;
        AmazonS3 s3Client=null;
        try {
            String s3Endpoint = "http://host.docker.internal:4566";
            s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Endpoint, region))
                    .withPathStyleAccessEnabled(true)
                    .build();

            isS3Available = s3Client.doesBucketExistV2("images");
        } catch (SdkClientException e) {
            isS3Available = false;
        }
        if (isS3Available) {


            S3Object s3Object = s3Client.getObject("images", id.toString());
            InputStream inputStream = s3Object.getObjectContent();

            Resource resource;
            try {
                resource = new InputStreamResource(inputStream);
            }
            catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book image not found!");
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        }
        else{
            String uploadDir = "book-photos/" + book.getId();
            Path path = Paths.get(uploadDir + "/" + fileName);
            Resource resource;
            try {
                resource = new UrlResource(path.toUri());
            } catch (MalformedURLException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book image not found!");
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ((UrlResource) resource).getFilename() + "\"")
                    .body(resource);
        }
    }


}
