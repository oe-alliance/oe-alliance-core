# Creates the "feed", packages not required for the image
# but that should be built for the feed so that other
# components may use them and install on demand.

require openpli-enigma2-image.bb

DEPENDS += "${OPTIONAL_PACKAGES}"
