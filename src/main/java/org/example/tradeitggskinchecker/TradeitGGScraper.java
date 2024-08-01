//package org.example.tradeitggskinchecker;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import java.util.logging.Logger;
//
//@Service
//public class TradeitGGScraper {
//    private static final String URL = "https://tradeit.gg/ru/csgo/trade";
//    private static final Logger LOGGER = Logger.getLogger(TradeitGGScraper.class.getName());
//
//    @Scheduled(fixedRate = 60000) // Обновлять каждые 60 секунд
//    public void checkForNewSkins() {
//        LOGGER.info("Checking for new skins on the website");
//
//        try {
//            getHTML();
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(URL))
//                    .build();
//
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            String html = response.body();
//
//            Document doc = Jsoup.parse(html);
//            Elements skins = doc.select(".item-name"); // Выбираем все элементы с именами скинов
//
//            for (Element skin : skins) {
//                String name = skin.text(); // Извлекаем имя скина
//                Element parent = skin.parent();
//                String price = parent.select(".price.gray-200--text.font-weight-bold.font-size-16.d-flex.align-center .d-inline-block").text(); // Извлекаем цену скина
//
//                LOGGER.info("Found skin: Name = " + name + ", Price = " + price);
//                if (isDesiredSkin(name, price)) {
//                    System.out.println("Name: " + name);
//                    System.out.println("Price: " + price);
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.severe("Error occurred while checking for new skins: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isDesiredSkin(String name, String price) {
//        // Добавьте логику фильтрации по вашим параметрам
//        return true; // Пример: вернуть true для всех скинов
//    }
//
//    public void getHTML()
//    {
//        try {
//            // Создаем клиент
//            HttpClient client = HttpClient.newHttpClient();
//
//            // Создаем запрос
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(URL))
//                    .build();
//
//            // Отправляем запрос и получаем ответ
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            // Получаем HTML-код
//            String html = response.body();
//            System.out.println("HTML Code:\n" + html);
//
//            // Можно сохранить HTML в файл
//            // Files.write(Path.of("page.html"), html.getBytes());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
