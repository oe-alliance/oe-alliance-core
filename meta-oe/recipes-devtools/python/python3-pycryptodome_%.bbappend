include ${PYTHON_PN}-package-split.inc

PROVIDES += "python3-pycrypto"
RPROVIDES:${PN} += "python3-pycrypto"
RCONFLICTS:${PN} = "python3-pycrypto"
RREPLACES:${PN} = "python3-pycrypto"
