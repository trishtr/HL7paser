import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.message.ADT_A01;
import ca.uhn.hl7v2.model.v23.message.ADT_A03;
import ca.uhn.hl7v2.model.v23.segment.EVN;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.PV1;
import ca.uhn.hl7v2.parser.PipeParser;

public class hl7Parser {
	
	public static boolean isCorrectHL7Format(String rawHL7Message)
	{
        // instantiate a PipeParser, which handles the "traditional or default encoding"
        PipeParser ourPipeParser = new PipeParser();

        try {
            // parse the string format message into a Java message object
            Message hl7Message = ourPipeParser.parse(rawHL7Message);
            System.out.println("HL7 Message:  " + hl7Message);
            System.out.println("Message is in the correctHL7 Format");
            return true;
            

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
		
    }
	
	
	public static void extractA01Format(String A01Message) throws HL7Exception {
		
		if(isCorrectHL7Format(A01Message))
		{	
			PipeParser ourPipeParser = new PipeParser();
			
			Message hl7Message = ourPipeParser.parse(A01Message);
			if (hl7Message instanceof ADT_A01) {
                ADT_A01 ackResponseMessage = (ADT_A01) hl7Message;

                //access message data and display it
                //note that I am using encode method at the end to convert it back to string for display
                
                //MSH
                MSH mshSegmentMessageData = ackResponseMessage.getMSH();
                //PV1
                PV1 PVMess = ackResponseMessage.getPV1();
                
                System.out.println("AdmissionType : " + PVMess.getAdmissionType().encode());
                
                //EVN
                EVN eventType = ackResponseMessage.getEVN();
                
                System.out.println("Message Type is " + mshSegmentMessageData.getMessageType().encode());
                System.out.println("Message Control Id is " + mshSegmentMessageData.getMessageControlID().encode());
                System.out.println("Message Timestamp is " + mshSegmentMessageData.getDateTimeOfMessage().encode());
                System.out.println("Sending Facility is " + mshSegmentMessageData.getSendingFacility().encode() + "\n");

                System.out.println("EventTypeCode " + eventType.getEventTypeCode().encode());
                
               
                

            }
		}
	}
	

	public void extractA03Format(String A03Message) throws HL7Exception {
		
		if(isCorrectHL7Format(A03Message))
		{	
			PipeParser ourPipeParser = new PipeParser();
			
			Message hl7Message = ourPipeParser.parse(A03Message);
			if (hl7Message instanceof ADT_A03) {
                ADT_A03 ackResponseMessage = (ADT_A03) hl7Message;

                //access message data and display it
                //note that I am using encode method at the end to convert it back to string for display
                MSH mshSegmentMessageData = ackResponseMessage.getMSH();
                
                EVN eventType = ackResponseMessage.getEVN();
                System.out.println("Message Type is " + mshSegmentMessageData.getMessageType().encode());
                System.out.println("Message Control Id is " + mshSegmentMessageData.getMessageControlID().encode());
                System.out.println("Message Timestamp is " + mshSegmentMessageData.getDateTimeOfMessage().encode());
                System.out.println("Sending Facility is " + mshSegmentMessageData.getSendingFacility().encode() + "\n");

                System.out.println("EventTypeCode " + eventType.getEventTypeCode());
                
                

                

            }
	
		}
	}
	
	public static void main (String args[]) {
	    String raw1 = "MSH|^~\\\\&|AFFINITYTEST|QAT|SHIFTWIZARDTEST|QAT|20220930023527||ADT^A01|00000001|Z|2.3|\\rEVN|A01|20220930023527|\\rPV1||I|2SFM^09^8^QAT|3||||||3MEDS||||1||||MED|20000006|||||||||||||||||||||||||20220930023043||\\r";
	    try {
            extractA01Format(raw1);
        } catch (HL7Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
