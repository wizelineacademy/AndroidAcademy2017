## Motions
### Simple motion for toolbar
 - add layout_scrollFlags scroll|enterAlways to the toolbar to have a basic motion of toolbar

### Simple motion for toolbar with tabs
 - add tabLayout to activity_main.xml
 - initiate the tab layout in the code
    - if we want to disappear the tabs too, we should add the same flags

### Motion with collapsingToolbarLayout
 - remove the tabs
 - add the CollapsingToolbarLayout 
 - change the height of AppBarLayout
 - add minHeight and CollapseMode to the toolbar
    - exitUntilCollapsed
    - enterAlways
    - enterAlwaysCollapsed
    - enterAlways | enterAlwaysCollapsed

### Motion with collapsingToolbarLayout with FAB
 - add an id to appBarLayout
 - add layout anchor and layout anchor gravity to FAB
 - remove the layout gravity from FAB 
 - change the collapsingToolbarLayout flag to exitUntilCollapsed
 
### Motion with collapsingToolbarLayout with FAB and parallax
 - add an imageView on the collapsingToolbarLayout
 - download an image and add it to drawable
 - add the values src, scaleType, collapseMode to image view
 - remove the backgroundColor to toolbar 
 - add contentScrim to CollapsingToolbarLayout
 
### Adding palette library
 - add the dependency to gradle
 - cast the collapsingToolbarLayout 
 - create a method to change the color of toolbar and status bar
 - implement the palette to get the colors
 
### example with palette 2 images 
