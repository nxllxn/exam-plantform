package exam.paperContext.domain.model.paper;

public class ResourceHasAlreadyExistedException extends RuntimeException {

    public ResourceHasAlreadyExistedException(String resourceName, String resourceId) {
        super(String.format("Resource [%s] with id [%s] has already existed", resourceName, resourceId));
    }
}
