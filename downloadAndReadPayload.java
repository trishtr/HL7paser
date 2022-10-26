import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.uhn.hl7v2.HL7Exception;

public class downloadAndReadPayload {

	public static void main(String[] args) throws HL7Exception {
		// TODO Auto-generated method stub
		String accessKey = "";
        String secretKey = "";
        String sessionToken = "";
                
        s3Connection connector = new s3Connection();
    AmazonS3 s3 = connector.s3Connection(accessKey, secretKey, sessionToken);
    S3Object fullObject = null;
    String bucketName = "";
    /*
    ListObjectsRequest lor = new ListObjectsRequest()
            .withBucketName("scm-data-raw")
            .withPrefix("APPRHS/2022/8/8")
            .withMaxKeys(10);
     */
    ListObjectReq req = new ListObjectReq();
    ListObjectsRequest lor = req.ListObjectReq("","APPRHS/2022/10/18", 40);

        try {
        /* Get first batch of objects in a given bucket */
        ObjectListing objects = s3.listObjects(lor);

        /* Recursively get all the objects inside given bucket */
        if(objects != null && objects.getObjectSummaries() != null) {
            while (true) {
                for(S3ObjectSummary summary : objects.getObjectSummaries()) {
                    System.out.println("Object Id :-" + summary.getKey());

                    //EventId is the same as the getKey();
                    //Found the approved eventid in the specific bucket

                    String key = summary.getKey();

                    System.out.println("Downloading an object");
                    fullObject = s3.getObject(new GetObjectRequest(bucketName, key));
                    System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
                    System.out.println("Content: ");
                    displayTextInputStream(fullObject.getObjectContent());

                    
                }
                break;
            }
        }

    } catch (AmazonServiceException | IOException e) {

        System.out.println(e);

    } finally {

        if(s3 != null) {
            s3.shutdown();
        }
    }
}
    private static void displayTextInputStream(InputStream input) throws IOException, HL7Exception {
        // Read the text input stream one line at a time and display each line.
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
           // System.out.println(line);
          
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            MessageContainer msgContainer = gson.fromJson(line, MessageContainer.class);
           
            //System.out.println("Payload: " + msgContainer.getPayload());
            
            Map eventData = (Map) msgContainer.getPayload().get("eventData");
            
            String rawHL7Message = (String) eventData.get("HL7");
            
            hl7Parser parser = new hl7Parser();
            parser.isCorrectHL7Format(rawHL7Message);
            
            //System.out.println("rawHL7: " + gson.toJson(msgContainer));
            
            if (rawHL7Message.contains("A03"))
            {
            	parser.extractA03Format(rawHL7Message);
            	
            }

        }
     
    }
		
	}
