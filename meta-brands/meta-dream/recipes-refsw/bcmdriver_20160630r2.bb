require bcmdriver.inc

SRC_URI[dm900.md5sum] = "678752f5c3a032b4884d8ee18070e529"
SRC_URI[dm900.sha256sum] = "d6d35877085d23f13e3d5e02bf149f890f9dd2ec8b6bb3ab87396c9ceeedf674"

COMPATIBLE_MACHINE = "dm900"

inherit opendreambox-precompiled-binary3
