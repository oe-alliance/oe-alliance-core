MODULE = "StreamInterface"
DESCRIPTION = "Stream webinterface on port 40080"

RDEPENDS_${PN} = "python-twisted-web"

require openplugins-replace-pli.inc

require openplugins-distutils.inc

require assume-gplv2.inc
