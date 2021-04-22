include python-package-split.inc

PROVIDES += "python-pycrypto"
RPROVIDES_${PN} += "python-pycrypto"
RCONFLICTS_${PN} = "python-pycrypto"
RREPLACES_${PN} = "python-pycrypto"
