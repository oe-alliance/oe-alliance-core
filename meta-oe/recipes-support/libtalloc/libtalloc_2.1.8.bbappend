FILESEXTRAPATHS_append := "${THISDIR}/files"

B = "${S}"

SRC_URI_append = " \
    file://talloc_old_kernel_fix.patch;patch=1 \
    "
