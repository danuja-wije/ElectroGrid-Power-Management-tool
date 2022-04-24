package controller;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import model.CreditCard;
import service.CreditCardService;
import service.CreditCardServiceImpl;


@Path("/CrecitCard")
public class CreditCardController {

	//Card service
	CreditCardService cardService = new CreditCardServiceImpl();
	
	ArrayList<CreditCard> cards= new ArrayList<>();;
	
	
	//Insert
	@POST
	@Path("/New")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCard(@FormParam("user_ID") String userID , @FormParam("card_number") String cardNumber, @FormParam("cvv") int cvv
			,@FormParam("date") String date, @FormParam("name_on_card") String name, @FormParam("card_issuer") String issuer) {
		
		String output = "";
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US)
			    .withResolverStyle(ResolverStyle.STRICT);
		
//		DateValidator validator = new DateValidatorUsingDateTimeFormatter(dateFormatter);
		
		
		if(cardNumber.length() > 19) {
			output = "Invalid card Number";
		}
		
		
		output = cardService.insertCreditCard(new CreditCard(
		userID, cardNumber, cvv, date, name, issuer));
		return output;
	}
	
	
	//View
	@GET
	@Path("/{user_ID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewCards(@PathParam("user_ID") String UID) {
		Gson gson = new Gson();
		cards = cardService.viewCards(UID);
		String jsonString  = gson.toJson(cards);
		return jsonString;
	}
	
	//Delete
	@DELETE
	@Path("/{card_number}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCards(@PathParam("card_number") String CardNum) {
		String response = cardService.deleteCard(CardNum);
		return response;
	}
	
	
	//Update
	@PUT
	@Path("/Updatecard/{old_card}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCard(@PathParam("old_card") String old_card , @FormParam("card_number") String cardNumber, @FormParam("cvv") int cvv
			,@FormParam("date") String date, @FormParam("name_on_card") String name, @FormParam("card_issuer") String issuer) {
		String output = cardService.updateCard(old_card, new CreditCard(
				null, cardNumber, cvv, date, name, issuer));
		return output;
	}
}