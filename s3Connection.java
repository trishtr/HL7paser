
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class s3Connection {
	
	public AmazonS3 s3Connection (String accessKey, String secretKey, String sessionToken) {
        BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
                accessKey, secretKey, sessionToken);

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
                .withRegion(Regions.US_EAST_1)
                .build();
        return s3;
/*        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b: buckets)
        {
            System.out.println("*" + b.getName());
        }

 */
    }

}
