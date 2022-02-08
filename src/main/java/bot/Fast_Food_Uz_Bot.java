package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.BotMenu;
import util.BotSettings;


public class Fast_Food_Uz_Bot extends TelegramLongPollingBot {

    /**
     * Bot username
     * @return - none
     */
    @Override
    public String getBotUsername() {
        return BotSettings.USER_NAME_BOT;
    }

    /**
     * Bot token
     * @return - none
     */
    @Override
    public String getBotToken() {
        return BotSettings.TOKEN;
    }

    /**
     * Bot start
     * @param update - All user operation
     */
    @Override
    public void onUpdateReceived(Update update) {
       if(update.hasMessage()){
           Message message = update.getMessage();
           SendMessage sendMessage = new SendMessage();

           if(message.hasText()){
               String text = message.getText();
               switch (text){
                   case BotMenu.START:
                   sendMessage = BotService.start(update);
                   break;
                   case BotMenu.MENU:
                       sendMessage = BotService.menu(message.getChatId());
                   break;
                   case BotMenu.CART:
                       sendMessage = BotService.showCart(message.getChatId());
                       break;
                   case BotMenu.DISCONT:
                       sendMessage = BotService.discont(message.getChatId());
                       break;
               }
               try {
                   execute(sendMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }
           } else if(message.hasContact()){
               Contact contact = message.getContact();
               sendMessage = BotService.getLocation(message.getChatId(), contact);
               try {
                   execute(sendMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }
           }
           else if(message.hasLocation()){
               Location location = message.getLocation();
               sendMessage = BotService.saveOrder(message.getChatId() , location);
               try {
                   execute(sendMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }
               DeleteMessage deleteMessage = new DeleteMessage(
                       message.getChatId().toString(),
                       message.getMessageId()
               );
               try {
                   execute(deleteMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }

           }

       }
       /**
        * comming - "category/5"
        * category - entrant
        * 5 - category id
        */
       else if(update.hasCallbackQuery()){
           String data = update.getCallbackQuery().getData();
           Message message = update.getCallbackQuery().getMessage();
           EditMessageText text;
           if(data.contains("category")) {
               long categoryId = Long.parseLong(data.substring(9).trim());
               text = BotService.getInlineMarkapCategory(message, categoryId);
               try {
                   execute(text);
               }catch (TelegramApiException e){
                   e.printStackTrace();
               }
           }
           /**
            * this are products same category_id
            * 2 or 3 id
            */
           else if    (data.contains("product/8") || data.contains("product/9" ) || data.contains("product/10") || data.contains("product/11") || data.contains("product/12")
                   || data.contains("product/13") || data.contains("product/14") || data.contains("product/31") || data.contains("product/32") || data.contains("product/33")
                   || data.contains("product/34") || data.contains("product/35") || data.contains("product/36") || data.contains("product/37")){
               long showProductId = Long.parseLong(data.substring(8).trim());
               EditMessageText editMessageText = BotService.getShowProduct(message , showProductId);
               try {
                   execute(editMessageText);
               }catch (TelegramApiException e){
                   e.printStackTrace();
               }
           }
           /**
            * comming "product/5"
            * product - entrant
            * 5 - subCategory id
            */
           else if(data.contains("product")){
               long productId = Long.parseLong(data.substring(8).trim());
               SendPhoto sendPhoto = BotService.showProduct(message, productId);
               try {
                   execute(sendPhoto);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }
               DeleteMessage deleteMessage = new DeleteMessage(
                       message.getChatId().toString(),
                       message.getMessageId()
               );
               try {
                   execute(deleteMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }
           }
           /**
            * comming "amount/3/5"
            * amount - entrant
            * 3 - product id
            * 5 - multiplication
            */
           else if(data.contains("amount")){
               String[] split = data.split("/");
               long productId = Long.parseLong(split[1].trim());
               int multiplication = Integer.parseInt(split[2].trim());

               SendMessage sendMessage = BotService.addProductToCart(message , productId , multiplication);

               try {
                   execute(sendMessage);
               }catch (TelegramApiException e){
                   e.printStackTrace();
               }
               /**
                * Deleted last message or inlineKeyBoardMarkup
                */
               DeleteMessage deleteMessage = new DeleteMessage(
                    message.getChatId().toString(),
                    message.getMessageId()
               );
               try {
                   execute(deleteMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }
           }
           else if(data.contains("send_order")){
               SendMessage sendMessage = BotService.orderCommit(message);
               try {
                   execute(sendMessage);
               }catch (TelegramApiException e){
                   e.printStackTrace();
               }
               /**
                * Deleted last message or inlineKeyBoardMarkup
                */
               DeleteMessage deleteMessage = new DeleteMessage(
                       message.getChatId().toString(),
                       message.getMessageId()
               );
               try {
                   execute(deleteMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }
           } else if(data.contains("cancel_order")){
               SendMessage sendMessage = BotService.orderCancel(message);
               try {
                   execute(sendMessage);
               }catch (TelegramApiException e){
                   e.printStackTrace();
               }
               /**
                * Deleted last message or inlineKeyBoardMarkup
                */
               DeleteMessage deleteMessage = new DeleteMessage(
                       message.getChatId().toString(),
                       message.getMessageId()
               );
               try {
                   execute(deleteMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }
           }
           else if(data.contains("back_menu")){
               SendMessage sendMessage = BotService.UserMenu(message);
               try {
                   execute(sendMessage);
               }catch (TelegramApiException e){
                   e.printStackTrace();
               }
               /**
                * Deleted last message or inlineKeyBoardMarkup
                */
               DeleteMessage deleteMessage = new DeleteMessage(
                       message.getChatId().toString(),
                       message.getMessageId()
               );
               try {
                   execute(deleteMessage);
               } catch (TelegramApiException e) {
                   e.printStackTrace();
               }
           }


       }
    }
}
