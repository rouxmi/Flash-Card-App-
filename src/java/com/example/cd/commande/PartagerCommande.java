package com.example.cd.commande;

public class PartagerCommande extends Commande {

    public PartagerCommande() {
        super(null, null, null);
    }

    @Override
    public void execute() throws Exception {
        String url = "https://drive.google.com/drive/folders/1lVTIiVpMvUISgtlbWErNU_TKBQSxMgqF?usp=sharing";
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open " + url);
            } else {
                // Assuming a Unix-like system
                String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };

                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++) {
                    if (Runtime.getRuntime().exec(new String[] {"which", browsers[count]}).waitFor() == 0) {
                        browser = browsers[count];
                    }
                }
                if (browser == null) {
                    throw new Exception("Could not find web browser");
                } else {
                    Runtime.getRuntime().exec(new String[] {browser, url});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

