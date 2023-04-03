package ASE.rest.mapper;

import ASE.entity.Cart;
import ASE.entity.User;
import ASE.entity.Book;
import ASE.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    UserGetDTO convertEntityToUserGetDTO(User user);


    @Mapping(source = "name", target = "name")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "publisher", target = "publisher")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "sellerid", target = "sellerid")
    @Mapping(source = "buyerid", target = "buyerid")
    @Mapping(source = "image", target = "image", qualifiedByName = "mapToBlob")
    Book convertBookPostDTOtoEntity(BookPostDTO bookPostDTO);

    @Named("mapToBlob")
    public default Blob mapToBlob(String bytes) throws SQLException {
        if (bytes == null) {
            return null;
        }
        try {
            return new SerialBlob(bytes.getBytes());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "publisher", target = "publisher")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "sellerid", target = "sellerid")
    @Mapping(source = "buyerid", target = "buyerid")
    @Mapping(source = "image", target = "image", qualifiedByName = "mapToString")
    BookGetDTO convertEntityToBookGetDTO(Book book);


    @Named("mapToString")
    public default String mapToString(Blob bytes) throws SQLException {
        if (bytes == null) return null;
        else
            return bytes.toString();
    }


    @Mapping(source = "books", target = "books")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "userId", target = "userId")
    Cart convertCartPostDTOtoEntity(CartPostDTO cartPostDTO);

    @Mapping(source = "books", target = "books")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "userId", target = "userId")
    CartGetDTO convertEntityToCartGetDTO(Cart cart);
}
