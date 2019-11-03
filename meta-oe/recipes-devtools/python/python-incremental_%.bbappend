include python-package-split.inc

# remove full python-twisted and python-click
RDEPENDS_${PN}_remove += " \
    ${PYTHON_PN}-twisted \
    ${PYTHON_PN}-click \
"
