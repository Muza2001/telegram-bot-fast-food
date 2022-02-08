package bot;

import dto.OrderCartDto;
import enums.BootState;
import enums.OrderCartStatus;
import model.*;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import service.*;
import service.serviceIMPL.*;
import util.BotConstants;
import util.BotMenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BotService {
    public static UserService userService = new UserServiceImpl();
    public static CategoryService categoryService = new CategoryServiceImpl();
    public static ProductService productService = new ProductServiceImpl();
    public static CartService cartService = new CartServiceImpl();
    public static OrderCartService orderCartService = new OrderCartServiceImpl();
    public static OrderService orderService = new OrderServiceImpl();
    public static OrderDetailService orderDetailService = new OrderDetailServiceImpl();

    /**
     *  /
     * Register user for new
     * @param update - user update All information
     * @return ReplayKeyBoard
     */

    public static SendMessage start(Update update) {
        // TODO: 1/9/2022  
        registerUser(update);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(BotConstants.MENU_HEADER);
        sendMessage.setReplyMarkup(getMenuMarkup());
        return sendMessage;
    }

    private static ReplyKeyboard getMenuMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(BotMenu.MENU));
        rows.add(keyboardRow1);

        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton(BotMenu.CART));
        keyboardRow2.add(new KeyboardButton(BotMenu.SETTING));
        rows.add(keyboardRow2);

        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow3.add(new KeyboardButton(BotMenu.INFO));
        keyboardRow3.add(new KeyboardButton(BotMenu.DISCONT));
        rows.add(keyboardRow3);

        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    private static void registerUser(Update update) {
        User from = update.getMessage().getFrom();
        if(!userService.exitsByChatId(update.getMessage().getChatId())){
          model.User user = new model.User(
                  update.getMessage().getChatId(),
                  from.getFirstName(),
                  from.getLastName(),
                  from.getUserName(),
                  update.getMessage().getContact() != null ? update.getMessage().getContact().getPhoneNumber() : "",
                  BootState.START
          );
            Long lastSavedId = userService.save(user);
            /**
             * CREATE NEW CART FOR USER
             */
            if(!cartService.existsByUserId(lastSavedId))
            cartService.save(new Cart(lastSavedId));
        }
    }

    /**
     *
     * @param chatId - Message chat id
     * @return - SendMessage Menu for user
     * First Menu in Category
     */

    public static SendMessage menu(Long chatId) {
        model.User user = userService.findByChatId(chatId);
        if(user != null){
            user.setBootState(BootState.SHOW_MENU);
            userService.update(user);
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(BotConstants.MENU_HEADER);

        List<Category> categories = categoryService.findAll();

        sendMessage.setReplyMarkup(getInlineKeyboardsCategory(categories));
        return sendMessage;
    }

    private static InlineKeyboardMarkup getInlineKeyboardsCategory(List<Category> categories) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> buttons;
        Iterator<Category> iterator = categories.iterator();

        while (iterator.hasNext()){
            Category next = iterator.next();
            buttons = new ArrayList<>();
            if(next.getParentId() != null) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(next.getPerefix() + " " + next.getName());
                inlineKeyboardButton.setCallbackData("category/" + next.getId().toString());
                buttons.add(inlineKeyboardButton);
            }
            if(iterator.hasNext()) {
                if (next.getParentId() != null) {
                    next = iterator.next();
                    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton(next.getPerefix() + " " + next.getName());
                    inlineKeyboardButton1.setCallbackData("category/" + next.getId().toString());
                    buttons.add(inlineKeyboardButton1);
                }
            }
            list.add(buttons);
        }
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    /**
     *
     * @param message - User chat id
     * @param categoryId - Category for user
     * @return - EditMessageText
     * InlineKeyBoards No1
     */

    public static EditMessageText getInlineMarkapCategory(Message message, long categoryId) {
        model.User user = userService.findByChatId(message.getChatId());
        if(user != null){
            user.setBootState(BootState.SHOW_CATEGORIES);
            userService.update(user);
        }

        List<Category> categories = categoryService.findbyCategoryId(categoryId);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setParseMode(ParseMode.MARKDOWN);
        editMessageText.setText(BotConstants.MENU_HEADER);

        editMessageText.setReplyMarkup(getInlineMarkapCategory(categories));
        return editMessageText;
    }

    private static InlineKeyboardMarkup getInlineMarkapCategory(List<Category> categories) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> buttons;
        Iterator<Category> iterator = categories.iterator();

        while (iterator.hasNext()){
            Category next = iterator.next();
            buttons = new ArrayList<>();
            if(next.getParentId() != null) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(next.getPerefix() + " " + next.getName());
                inlineKeyboardButton.setCallbackData("product/" + next.getId().toString());
                buttons.add(inlineKeyboardButton);
            }
            if(iterator.hasNext()) {
                if (next.getParentId() != null) {
                    next = iterator.next();
                    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton(next.getPerefix() + " " + next.getName());
                    inlineKeyboardButton1.setCallbackData("product/" + next.getId().toString());
                    buttons.add(inlineKeyboardButton1);
                }
            }
            list.add(buttons);
        }
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    /**
     *
     * @param message - User id
     * @param productId - get product for id
     * @return - return SendPhot
     * InlineKeyBoards No2
     */

    public static SendPhoto showProduct(Message message, long productId) {
        model.User user = userService.findByChatId(message.getChatId());
        if(user != null){
            user.setBootState(BootState.SELECT_PRODUCT);
            userService.update(user);
        }
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        Product product = productService.findByProductId(productId);
        if (product == null){
            product = productService.findbyCategoryId(productId);
        }
        sendPhoto.setPhoto(new InputFile(product.getImageUrl()));
        sendPhoto.setCaption(product.getName() + "\n\nPrice" + product.getPrice() + "\nComposition:" + "\n" + product.getComposition());

        sendPhoto.setReplyMarkup(getInlineMarkupForOrder(productId));
        return sendPhoto;
    }

    private static InlineKeyboardMarkup getInlineMarkupForOrder(long productId) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> list = new ArrayList<>();
            int count = 1;
        for (int i = 0; i < 3; i++) {
            List<InlineKeyboardButton> buttons= new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                InlineKeyboardButton button = new InlineKeyboardButton(count + "");
                button.setCallbackData("amount/" + productId + "/" + count);
                buttons.add(button);
                count++;
            }
            list.add(buttons);
        }
            inlineKeyboardMarkup.setKeyboard(list);

            return inlineKeyboardMarkup;
        }

    /**
     * @param message - message need for user
     * @param productId - product Inline Key board
     * @param multiplication - product multiplication
     * @return - product or + Inline key board
     */
    public static SendMessage addProductToCart(Message message, long productId, int multiplication) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Mahsulot Savatchaga qo'shildi !");

        model.User user = userService.findByChatId(message.getChatId());
        if(user != null){
            user.setBootState(BootState.SELECT_PRODUCT_COUNT);
            userService.update(user);
        }
        if (user == null || user.getId() == null){
            sendMessage.setText("User topilmadi");
            return sendMessage;
        }
        Cart cart = cartService.findByUserId(user.getId());
        if (cart == null || cart.getId() == null){
            sendMessage.setText("Cart not found with user {"+ user.getId() +" }");
            return sendMessage;
        }

        Product product = productService.findByProductId(productId);
        if(product == null){
            product = productService.findbyCategoryId(productId);
        }
        if(product == null || product.getId() == null){
            sendMessage.setText("Not found product");
            return sendMessage;
        }

        OrderCart orderCart = orderCartService.findByCartAndProduct(cart.getId(),product.getId());

        if(orderCart == null){
            orderCartService.save(new OrderCart(
                    cart.getId(),
                    product.getId(),
                    multiplication,
                    (product.getPrice() * multiplication),
                    OrderCartStatus.OPEN,
                    false
            ));
        } else {

        }


        return sendMessage;
    }

    public static EditMessageText getShowProduct(Message message, long showProductId) {
        model.User user = userService.findByChatId(message.getChatId());
        if(user != null){
            user.setBootState(BootState.SHOW_PRODUCTS);
            userService.update(user);
        }
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setParseMode(ParseMode.MARKDOWN);
        editMessageText.setText(BotConstants.MENU_HEADER);

        List<Product> products = productService.findByProductForId(showProductId);

            editMessageText.setReplyMarkup(getInlineKeyboardsShowProducts(products));
            return editMessageText;
        }

    private static InlineKeyboardMarkup getInlineKeyboardsShowProducts(List<Product> products) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> buttons;
        Iterator<Product> iterator = products.iterator();

        while (iterator.hasNext()){
            Product next = iterator.next();
            buttons = new ArrayList<>();
            if(next.getCategory_id() != null) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(next.getPrice() + " " + next.getName());
                inlineKeyboardButton.setCallbackData("product/" + next.getId().toString());
                buttons.add(inlineKeyboardButton);
            }
            if(iterator.hasNext()) {
                if (next.getCategory_id() != null) {
                    next = iterator.next();
                    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton(next.getPrice() + " " + next.getName());
                    inlineKeyboardButton1.setCallbackData("product/" + next.getId().toString());
                    buttons.add(inlineKeyboardButton1);
                }
            }
            list.add(buttons);
        }
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    /**
     * ReplayKeyMarkup for cart
     */

    public static SendMessage showCart(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(BotConstants.MENU_HEADER);
        model.User user = userService.findByChatId(chatId);
        if(user != null){
            user.setBootState(BootState.SHOW_CART);
            userService.update(user);
        }
        if (user == null){
            sendMessage.setText("User not foun");
            return sendMessage;
        }
        Cart byId = cartService.findById(user.getId());
        OrderCart byId1=null;
        if (byId != null) {
            byId1 = orderCartService.findById(byId.getId());
        }

        if (byId1 == null){
            sendMessage.setText("Cart not foun");
            return sendMessage;
        }

        List<OrderCartDto> orderCartDtos = orderCartService.findByCartId(byId.getId());
        if (orderCartDtos.isEmpty()){
            sendMessage.setText("Savatcha bo'sh");
        }
        double price = 0d;
        StringBuilder text = new StringBuilder("\n\nSavatchada\n\n");
        for (int i = 1; i <= orderCartDtos.size(); i++) {
            OrderCartDto dto = orderCartDtos.get(i -1);
            text.append(i).append(".\t").append(dto.getProduct_name()).append("\t").append(dto.getProduct_price()).append(" X ").append(dto.getAmount()).append(" = ").append(dto.getProduct_price() * dto.getAmount()).append("\n\n");
            price += dto.getAmount() * dto.getProduct_price();
        }
        text.append("\nJami   ").append(price).append("       so'm");
        sendMessage.setText(text.toString());

        sendMessage.setReplyMarkup(getReplayKeyBoardMarkup(orderCartDtos));

        return sendMessage;
    }

    private static ReplyKeyboard getReplayKeyBoardMarkup(List<OrderCartDto> orderCartDtos) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        Iterator<OrderCartDto> iterator = orderCartDtos.iterator();

        while (iterator.hasNext()) {
            OrderCartDto orderCartDto = iterator.next();
            List<InlineKeyboardButton> buttons1 = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("❌  " + orderCartDto.getProduct_name());
            inlineKeyboardButton.setCallbackData(BotConstants.ORDER_CART + BotConstants.PREFIX + orderCartDto.getId());
            buttons1.add(inlineKeyboardButton);
            if (iterator.hasNext()) {
                orderCartDto = iterator.next();
                inlineKeyboardButton = new InlineKeyboardButton("❌  " + orderCartDto.getProduct_name());
                inlineKeyboardButton.setCallbackData(BotConstants.ORDER_CART + BotConstants.PREFIX + orderCartDto.getId());
                buttons1.add(inlineKeyboardButton);
            }
            buttons.add(buttons1);
        }
            List<InlineKeyboardButton> buttons2 = new ArrayList<>();
            InlineKeyboardButton sendButton = new InlineKeyboardButton("✅ Buyurtmani jonatish");
            sendButton.setCallbackData("send_order");
            buttons2.add(sendButton);


            InlineKeyboardButton cancelOrder = new InlineKeyboardButton("❌  Buyurtmani bekor qilish");
            cancelOrder.setCallbackData("cancel_order");
            buttons2.add(cancelOrder);
            buttons.add(buttons2);

            buttons2 = new ArrayList<>();
            InlineKeyboardButton home = new InlineKeyboardButton("\uD83C\uDFD8 Ortga qaytish");
            home.setCallbackData("back_menu");
            buttons2.add(home);

            buttons.add(buttons2);


        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

    /**
     * Discont - menu
     * @return - product_id
     */
    public static SendMessage discont(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(BotConstants.MENU_HEADER);
        model.User user = userService.findByChatId(chatId);
        if(user != null){
            user.setBootState(BootState.SHOW_CART);
            userService.update(user);
        }

        Random random = new Random();

        int count = 0;
        List<Product> products = new ArrayList<>();
        while (count <= 8){
            int i = random.nextInt();
            if(i > 228 && i < 283){
               products.add(productService.findByRandom(i));
                count++;
            }
        }

        sendMessage.setText("Aksiya doyirasidagi mahsulotlar!!!");
        sendMessage.setReplyMarkup(discontMenuInlineKeyBoard(products));

        return sendMessage;
    }

    private static ReplyKeyboard discontMenuInlineKeyBoard(List<Product> products) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> buttons;
        Iterator<Product> iterator = products.iterator();

        while (iterator.hasNext()){
            Product next = iterator.next();
            buttons = new ArrayList<>();
            if(next.getCategory_id() != null) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(next.getPrice() + " " + next.getName());
                inlineKeyboardButton.setCallbackData("product/" + next.getId().toString());
                buttons.add(inlineKeyboardButton);
            }
            if(iterator.hasNext()) {
                if (next.getCategory_id() != null) {
                    next = iterator.next();
                    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton(next.getPrice() + " " + next.getName());
                    inlineKeyboardButton1.setCallbackData("product/" + next.getId().toString());
                    buttons.add(inlineKeyboardButton1);
                }
            }
            list.add(buttons);
        }
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    /**
     * Save order_cart for order_deteil and ordr_for_user
     *
     */
    public static SendMessage orderCommit(Message message) {
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(BotConstants.MENU_HEADER);
        model.User user = userService.findByChatId(chatId);
        if(user != null){
            user.setBootState(BootState.GET_CONTACT);
            userService.update(user);
        }
        if (user == null || user.getId() == null){
            sendMessage.setText("User not found");
            return sendMessage;
        }

        // TODO: 1/22/2022 User dagi phone numberga contactni qoshish

        sendMessage.setText("Raqamingizni jo'nating");
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("\uD83D\uDCDE Bosing");
        button.setRequestContact(true);
        row.add(button);
        rows.add(row);
        replyKeyboard.setKeyboard(rows);
        sendMessage.setReplyMarkup(replyKeyboard);

        return sendMessage;
    }

    public static SendMessage getLocation(Long chatId, Contact contact) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(BotConstants.LOCATION);

        model.User user = userService.findByChatId(chatId);
        if(user != null){
            user.setBootState(BootState.GET_CONTACT);
            userService.update(user);
        }
        if (user == null || user.getId() == null){
            sendMessage.setText("User not found");
            return sendMessage;
        }

        userService.saveNewContact(contact.getPhoneNumber(), user.getId());

        sendMessage.setText("Locationni jo'nating");
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("\uD83D\uDCCD Bosing");
        button.setRequestLocation(true);
        row.add(button);
        rows.add(row);
        replyKeyboard.setKeyboard(rows);
        sendMessage.setReplyMarkup(replyKeyboard);
        return sendMessage;
    }

    public static SendMessage saveOrder(Long chatId, Location location) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(BotConstants.SAVE_ORDER);
        model.User user = userService.findByChatId(chatId);
        if(user != null){
            user.setBootState(BootState.GET_LOCATION);
            userService.update(user);
        }
        if (user == null || user.getId() == null){
            sendMessage.setText("User not found");
            return sendMessage;
        }
        if (user.getCurrentLatitude() != null && user.getCurrentLongitude() != null){
            SendLocation sendLocation = new SendLocation();
            sendLocation.setLatitude(user.getCurrentLatitude());
            sendLocation.setLongitude(user.getCurrentLongitude());
            sendLocation.setChatId(user.getChatId().toString());
            sendMessage.setText("Manzilingiz tog'rimo yoki boshqa manzilmi?");

        }
        userService.updateLocation(user.getChatId(), location);

        // TODO: 1/22/2022 Order va OrderDetailga buyurtmani yozish

        sendMessage.setText("Buyurtmangiz qabul qilindi aperatorlar bog'lanishini kuting !!!");

        Cart cart = cartService.findById(user.getId());

        OrderCart orderCart = orderCartService.findById(cart.getId());

        orderService.saveOrder(orderCart.getTotalPrice(), user.getId());

        orderDetailService.saveDetailForProduct(orderCart);

        cartService.removeAll(cart.getId());

        sendMessage.setReplyMarkup(getMenuMarkup());

        return sendMessage;
    }

    /**
     * REMOVE ALL order_cart
     */
    public static SendMessage orderCancel(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(BotConstants.SAVE_ORDER);
        model.User user = userService.findByChatId(message.getChatId());
        if(user != null){
            user.setBootState(BootState.CANCEL);
            userService.update(user);
        }
        if (user == null || user.getId() == null){
            sendMessage.setText("User not found");
            return sendMessage;
        }

        Cart cart = cartService.findById(user.getId());

        cartService.removeAll(cart.getId());

        return sendMessage;
    }

    /**
     * BACK menu
     */
    public static SendMessage UserMenu(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(BotConstants.SAVE_ORDER);
        model.User user = userService.findByChatId(message.getChatId());
        if(user != null){
            user.setBootState(BootState.SHOW_MENU);
            userService.update(user);
        }
        if (user == null || user.getId() == null){
            sendMessage.setText("User not found");
            return sendMessage;
        }
        sendMessage.setReplyMarkup(getMenuMarkup());

        return sendMessage;
    }
}
