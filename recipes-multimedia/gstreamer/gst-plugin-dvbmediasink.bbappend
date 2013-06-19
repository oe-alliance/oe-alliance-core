PRINC = "7"

SRC_URI_append= " \
	file://0001-Fix-for-flac-files-with-high-sample-rates.patch \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"
