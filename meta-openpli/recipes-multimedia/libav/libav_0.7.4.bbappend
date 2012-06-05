PRINC = "1"

DEPENDS += " \
		schroedinger \
		libgsm \
	"

EXTRA_OECONF += " \
		--enable-libgsm \
		--enable-libschroedinger \
	"

RSUGGESTS_${PN} = ""
