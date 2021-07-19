package pl.kamilszela.view;

public enum ColorTheme {
    LIGHT,
    DARK;

    public static String getStylesheetPath(ColorTheme colorTheme){
        switch (colorTheme){
            case LIGHT:
                return "/css/themeLight.css";
            case DARK:
                return "/css/themeDark.css";
            default:
                return null;
        }
    }
}
