include python-package-split.inc

RDEPENDS_${PN}_append = " python-soupsieve"

PROVIDES += "python-beautifulsoup"
RPROVIDES_${PN} += "python-beautifulsoup"
