PRINC = "3"

MACHINE_ESSENTIAL_EXTRA_DEPENDS ?= ""

# Make sure we build extra machine depends
DEPENDS += " \
	${MACHINE_ESSENTIAL_EXTRA_DEPENDS} \
	"
