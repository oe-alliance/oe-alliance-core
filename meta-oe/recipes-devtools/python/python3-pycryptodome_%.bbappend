include ${PYTHON_PN}-package-split.inc

PROVIDES += "python3-pycrypto"
RPROVIDES_${PN} += "python3-pycrypto"
RCONFLICTS_${PN} = "python3-pycrypto"
RREPLACES_${PN} = "python3-pycrypto"
