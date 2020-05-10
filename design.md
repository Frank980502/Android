# Technical Design of UL Timetable
* The main components and technologies used in the timetable app:
  * activity
  * fragment
  * service
  * Firebase Authentication
  * Firebase Cloud Firestore
  * databinding
  * google map

* Which technical design choices did you make?
  * Use Firebase Authentication to implement the registration and login functions.
  * Use Firebase Cloud Firestore as the app's database.
  * Use DrawerLayout and NavigationView with the Menu to implement Navigation.
  * Use ViewPager to contain 5 fragments, from Monday to Friday, and add PagerTabStrip to show the title.
  * Use databinding to update UI when the data is changed.
  * Use service to send a notification when user uses the note reminder function. 
  * Use Google map to show the user's location and the classroom's location.

* Any lessons learned? E.g., what would you do differently next time?
  * Designing the reasonable and effective data structure is really important.
  * Make more use of threads.
  * Read and study more about the offical documents.
