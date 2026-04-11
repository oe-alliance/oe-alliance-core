SRCDATE = "20200625"
SRCDATE:h8se = "20260402"

require airdigital-libs.inc

SRC_URI[sha256sum] = "0688f48de6ab3bb39ee1729043404bbd6c83e6a12bf251b9d758a86b2dffa6ee"
SRC_URI[h8se.sha256sum] = "c570f05ea36a0b8c462de6d5c7e83f77d26ba635b8b5c8d268accd444cf9fdad"

COMPATIBLE_MACHINE = "^h8se$|^h9$|^h9se$|^h9combo$|^h9combose$|^h10$|^i55plus$|^i55se$"
