Transformer
===========

A transformer model that can be manipulated directly by mouse. It's developed with Java 2D library.

======================================================================
Transformer User Interactions
======================================================================

Note: I adjusted the rotation constraint according to my doll content.
e.g. It doesn't make sense to rotate the transformer's lower arms back
160 degrees since they will get stuck by the armor.

1. The transformer itself is very intuitive by nature. When users see
the transformer, the first thing they try will be to perform
transformation on it.

2. If the mouse hovers over the background, a tooltip will show to tell
users that they can play with the transformer by dragging the body
parts.

3. If the mouse hovers over any body part, a corresponding tooltip will
show to indicate what kind of transformation users can play. The
rotation, dragging, and stretching transformation is showed with an
icon so that users can get the information more intuitively without
necessarily reading all the words. I got this suggestion from many of my
friends, I also like it since it's very simple, intuitive, and informative.

4. Users can stretch the legs by dragging with right mouse button, which
is also showed in the tooltip of legs.


Screenshots:

![alt tag](https://raw.github.com/yuduozhang/Transformer/master/Screenshot/1.png)

![alt tag](https://raw.github.com/yuduozhang/Transformer/master/Screenshot/2.png)

![alt tag](https://raw.github.com/yuduozhang/Transformer/master/Screenshot/3.png)
