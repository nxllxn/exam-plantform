package exam.paperContext.domain.model.paper;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String resourceId) {
        super(String.format("Resource [%s] with id [%s] not found", resourceName, resourceId));
    }
}
