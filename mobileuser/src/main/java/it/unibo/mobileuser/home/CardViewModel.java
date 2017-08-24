package it.unibo.mobileuser.home;

/**
 * A class that create an object to put in a CardView.
 */
public class CardViewModel {

    private final String title;
    private final int icon;
    private final String activity;

    /**
     * Constructor to create an object with the fields of a CardView.
     *
     * @param title    the title of the CardView
     * @param icon     the icon show on the CardView
     * @param activity the path of activity that open the CardView
     */
    public CardViewModel(final String title, final int icon, final String activity) {
        this.title = title;
        this.icon = icon;
        this.activity = activity;
    }

    /**
     * Return the title of the CardView.
     *
     * @return CardView title
     */
    protected String getTitle() {
        return this.title;
    }

    /**
     * Return the icon of the CardView.
     *
     * @return CardView icon
     */
    protected int getIcon() {
        return this.icon;
    }

    /**
     * Return the path of activity open by the CardView.
     *
     * @return the path of activity
     */
    protected String getActivity() {
        return this.activity;
    }
}