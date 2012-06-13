MODULE = "trunk"
OSCAMBIN = "oscam-experimental"
OSCAMDEPENS = "openssl"
OSCAMRDEPENDS ="openssl"
URI = "svn://oscam.to/svn/oscam;module=trunk;proto=http;scmdata=keep"
SSL = "-DWITH_SSL=1"
PCSC = "-DHAVE_PCSC=0"
LIBUSB = "-DHAVE_LIBUSB=1"
ALTERNATIVE_PRIORITY = "20"
require oscam-bin.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
