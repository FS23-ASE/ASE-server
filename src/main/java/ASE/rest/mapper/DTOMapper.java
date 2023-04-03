package ASE.rest.mapper;

import ASE.entity.User;
import ASE.entity.Book;
import ASE.rest.dto.BookGetDTO;
import ASE.rest.dto.BookPostDTO;
import ASE.rest.dto.UserGetDTO;
import ASE.rest.dto.UserPostDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

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
    //@Mapping(source = "image", target = "image")
    @Mapping(source = "sellerid", target = "sellerid")
    @Mapping(source = "buyerid", target = "buyerid")
    Book convertBookPostDTOtoEntity(BookPostDTO bookPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "publisher", target = "publisher")
    @Mapping(source = "status", target = "status")
    //@Mapping(source = "image", target = "image")
    @Mapping(source = "sellerid", target = "sellerid")
    @Mapping(source = "buyerid", target = "buyerid")
    BookGetDTO convertEntityToBookGetDTO(Book book);

}
