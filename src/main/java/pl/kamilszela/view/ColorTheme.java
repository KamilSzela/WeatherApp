package pl.kamilszela.view;

public enum ColorTheme {
    LIGHT,
    DARK;

    public static String getStylesheetPath(ColorTheme colorTheme){
        switch (colorTheme){
            case DARK:
                return "/css/themeDark.css";
            case LIGHT:
            default:
                return "/css/themeLight.css";
        }
    }
}
