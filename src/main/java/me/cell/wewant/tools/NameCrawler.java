package me.cell.wewant.tools;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NameCrawler {

    static Boolean isDir(Path path) {
        if (path == null || !Files.exists(path)) return false;
        else return Files.isDirectory(path);
    }

    public static void main(String[] args) {


        try {
//            String dirName = "/Users/cell/Downloads/.a/";
//            String dirName = "/Volumes/Disk/share";
//            String dirName = "/Volumes/share/lh/";
//            String dirName = "/Volumes/share/download/";
//            String dirName = "/Volumes/Elements6t/.a/";
            String dirName = "/Volumes/cell-rog/A";

            Files.list(new File(dirName).toPath()).limit(500).forEach(path -> {
//                        if (!path.toFile().isDirectory()) {
                String originalVideoName = path.toFile().getName();
//                        System.out.println(originalVideoName);
//                        path.getFileName().toString()
//                        String fullname = path.toString();
//                        System.out.println(originalVideoName);
                if (originalVideoName.length() < 16 && originalVideoName.length() > 6 && !originalVideoName.equals(".DS_Store") && !isDir(path)) {
                    String videoQueryName = originalVideoName.replace("hhd800.com@", "").replace(".mp4", "").replace("-4k", "").replace("-4K", "").replace("-C", "").replace("-c", "").toUpperCase();
                    System.out.println("full path:" + path);
                    System.out.println("query name:" + videoQueryName);
                    try {
                        String name = getname(videoQueryName);
                        if (name != null) {
                            String oldname = path.toString();
                            File file = new File(oldname);

                            if (originalVideoName.contains("-c") || originalVideoName.contains("-C")) {
                                name = name.replace(videoQueryName, videoQueryName + "-C");
                            }

                            if (originalVideoName.contains("-4k") || originalVideoName.contains("-4K")) {
                                name = name.replace(videoQueryName, videoQueryName + "-4K");
                            }


                            String finalname = oldname.replace(originalVideoName, name + ".mp4");

                            System.out.println(oldname + "===>>>>" + finalname);

                            File file2 = new File(finalname);
                            boolean success = file.renameTo(file2);
                        }

                        Thread.sleep((long) (Math.random() * 15000));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getname(String vcode) {
        try {

            OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).build();
            Request home = new Request.Builder().url("https://www.javbus.com/" + vcode).build();

            Response homeresp = null;

            homeresp = client.newCall(home).execute();

            if (homeresp.code() == 200) {
                String string = homeresp.body().string();
                homeresp.close();
                return getYanyuan(string);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void daka() throws IOException {


        OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).build();


        Request home = new Request.Builder().url("https://ydy1.com/").build();

        Response homeresp = client.newCall(home).execute();
        System.out.println(homeresp.header("set-cookie"));

        RequestBody formBody = new FormBody.Builder().add("username", "chengbo").add("password", "chengboc").build();

        Request login = new Request.Builder().url("https://ydy1.com/user/login").post(formBody).build();

        Call call = client.newCall(login);
        Response loginResponse = call.execute();
        System.out.println(loginResponse.header("cookie"));
        System.out.println(login.header("cookie"));
        System.out.println(loginResponse.code());
        System.out.println(loginResponse.body().string());

        RequestBody checkinformBody = new FormBody.Builder()

                .build();

        Request checkin = new Request.Builder().url("https://ydy1.com/core/check_in/").post(checkinformBody).build();
        Response checkinresp = client.newCall(checkin).execute();
        System.out.println(checkinresp.body().string());
        System.out.println(checkinresp.code());
    }

    public static String getYanyuan(String html) {

        Document doc = Jsoup.parse(html);
        String name = "";
        Elements select = doc.select("span[onmouseover]").select("span[onmouseout]");
        for (Element element : select) {
            name = element.text();
        }

        String title = null;
        Elements selecth3 = doc.select("h3");
        for (Element element : selecth3) {
            title = element.text();
        }

        if (title.endsWith(name)) {
            title = title.substring(0, title.length() - name.length()).trim();
        }
        return name + " " + title;
    }

    @NotNull
    private static String testhtml() {

        return "";
    }


}
