package agalvezmarco.wdil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AdaptadorPager extends FragmentPagerAdapter {

    private int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] {"Series", "Manga", "Libros"};

    public AdaptadorPager(FragmentManager f) {
        super(f);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        switch (position) {
            case 0:
                f = new Series_fragment();
                break;
            case 1:
                f = new Manga_Fragment();
                break;
            case 2:
                f = new Libro_Fragment();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
