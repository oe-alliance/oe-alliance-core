FILESEXTRAPATHS:prepend := "${THISDIR}/gcc-15.2:"

SRC_URI:append = " \
           file://0005-optional-libstdc.patch \
           file://0028-libgomp-Fix-const-qualifier-error-with-glibc-2.43.patch \
"