public class TooltipHelper {

    public static String getAshCrucibleTooltip() {
        return "<html><body style='width: 250px;'>" +
                "<b><font color='#AAAAAA'>⚙️ Ash Crucible</font></b><br>" +
                "Construct a Crucible to passively gather Ash over time.<br><br>" +
                "<b>Cost:</b> <font color='#FF5555'>10 Ash</font><br>" +
                "<b>Time:</b> <font color='#AAAAFF'>3s interval</font><br>" +
                "<b>Effect:</b> +1 Ash every 3s<br><br>" +
                "<i>The crucible burns low and steady, never hungering.</i>" +
                "</body></html>";
    }

    public static String getTendTooltip() {
        return "<html><body style='width: 250px;'>" +
                "<b><font color='#FFA500'>🕯️ Tend the Flame</font></b><br>" +
                "Manually coax ash from the sacred Flame.<br><br>" +
                "<b>Effect:</b> +1 Ash (or +2 if Insight unlocked)<br><br>" +
                "<i>The Flame watches... ever waiting.</i>" +
                "</body></html>";
    }

    public static String getFlameboundInsightTooltip() {
        return "<html><body style='width: 250px;'>" +
                "<b><font color='#66FF66'>💡 Flamebound Insight</font></b><br>" +
                "Unlocks deeper understanding of the Flame, doubling manual Ash gain.<br><br>" +
                "<b>Cost:</b> <font color='#FFA500'>20 Embered Ash</font><br>" +
                "<b>Effect:</b> Tend & click yield +2 Ash<br><br>" +
                "<i>Whispers curl through the smoke. You listen.</i>" +
                "</body></html>";
    }

    public static String getRuneboundCrucibleTooltip() {
        return "<html><body style='width: 250px;'>" +
                "<b><font color='#00BFFF'>💠 Runebound Crucible</font></b><br>" +
                "Inscribe your Crucible with runes to double its yield.<br><br>" +
                "<b>Cost:</b> <font color='#FFA500'>30 Embered Ash</font><br>" +
                "<b>Effect:</b> +2 Ash per 3s<br><br>" +
                "<i>The runes glow softly... feeding the Flame’s thirst.</i>" +
                "</body></html>";
    }

    public static String getFlameTooltip() {
        return "<html><body style='width: 200px;'>" +
                "<b><font color='#FF4500'>🔥 The Flame</font></b><br>" +
                "Click to draw power — gather Ash from its heat.<br><br>" +
                "<i>It remembers.</i>" +
                "</body></html>";
    }

    public static String getEmberAshTooltip() {
        return "<html><body style='width: 250px;'>" +
                "<b><font color='#FF9900'>🔥 Ember Ash</font></b><br>" +
                "Convert raw Ash into Embered Ash using the Flame Scepter.<br><br>" +
                "<b>Cost:</b> <font color='#FF5555'>5 Ash</font><br>" +
                "<b>Effect:</b> +1 Embered Ash<br><br>" +
                "<i>The ember pulses with your will.</i>" +
                "</body></html>";
    }
}
