# GObject introspection is target-side functionality.
# The SDK variant must not depend on a non-existing nativesdk-gobject-introspection provider.
PACKAGECONFIG:remove:class-nativesdk = "introspection"
