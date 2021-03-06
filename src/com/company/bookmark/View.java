package com.company.bookmark;

import com.company.bookmark.constants.KidsFriendlyStatus;
import com.company.bookmark.constants.UserType;
import com.company.bookmark.controllers.Controller;
import com.company.bookmark.entities.Bookmark;
import com.company.bookmark.entities.User;
import com.company.bookmark.partner.Shareable;

import java.util.List;

public class View {
  /*  public static void bookmark(User user, Bookmark[][] bookmarks){
        System.out.println("\n"+user.getEmail()+" is bookmarking");
        for (int i=0;i<DataStore.USER_BOOKMARK_LIMIT;i++)
        {
            int typeOffSet = (int) (Math.random() * DataStore.BOOKMARK_TYPES_COUNT);
            int bookmarkOffSet = (int) (Math.random() * DataStore.BOOKMARK_COUNT_PER_TYPE);
            Bookmark bookmark = bookmarks[typeOffSet][bookmarkOffSet];
            Controller.getInstance().saveUserBookmark(user,bookmark);
            System.out.println(bookmark);
        }
    }
  */
    public static void browse(User user, List<List<Bookmark>> bookmarks)
    {
        System.out.println("\n"+user.getEmail()+" is bookmarking");
        int bookmarkCount=0;
        for (List<Bookmark> bookmarklist:bookmarks) {
            for (Bookmark bookmark:bookmarklist) {
               // if (bookmarkCount<DataStore.USER_BOOKMARK_LIMIT){
                   boolean isBookmarked = getBookmarkDecision(bookmark);
                   if (isBookmarked) {
                       bookmarkCount++;
                       Controller.getInstance().saveUserBookmark(user,bookmark);
                       System.out.println("New bookmark"+bookmark);
                   }
                 //  }
                //isKidsFriendly
                if (user.getUserType().equals(UserType.CHIEF_EDITOR)||user.getUserType().equals(UserType.EDITOR))
                {
                    if (bookmark.getKidFriendlyStatus().equals(KidsFriendlyStatus.UNKNOWN)&&bookmark.isKidFriendly()){
                        KidsFriendlyStatus kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
                        if(!kidFriendlyStatus.equals(KidsFriendlyStatus.UNKNOWN)) {
                            Controller.getInstance().setKidFriendlyStatus(kidFriendlyStatus,user,bookmark);

                        }
                    }
                    if (bookmark.getKidFriendlyStatus().equals(KidsFriendlyStatus.UNKNOWN)&& bookmark instanceof Shareable){
                        boolean isShared=getShareDecision();
                        if (isShared){
                            Controller.getInstance().share(user,bookmark);
                        }
                    }
                }
                }
            }

        }

    private static boolean getShareDecision() {
        return Math.random() < 0.5;
    }

    private static KidsFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmark) {
        return Math.random()<0.4?KidsFriendlyStatus.APPROVED:(Math.random()>=0.4&&Math.random()<0.8)?KidsFriendlyStatus.REJECTED:KidsFriendlyStatus.UNKNOWN;
    }

    private static boolean getBookmarkDecision(Bookmark bookmark) {
        return Math.random() < 0.5;

    }

}
