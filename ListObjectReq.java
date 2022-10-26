
import com.amazonaws.services.s3.model.ListObjectsRequest;

public class ListObjectReq {

	public ListObjectsRequest ListObjectReq(String bucketName, String preFix, int maxKeys){
        ListObjectsRequest lor = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(preFix)
                .withMaxKeys(maxKeys);
        return lor;
    }
}
