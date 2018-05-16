################################################################################
#
# gst-fluendo-mpegdemux
#
################################################################################

GST_FLUENDO_MPEGDEMUX_VERSION = 0.10.72
GST_FLUENDO_MPEGDEMUX_SOURCE = gst-fluendo-mpegdemux-$(GST_FLUENDO_MPEGDEMUX_VERSION).tar.bz2
GST_FLUENDO_MPEGDEMUX_SITE = http://core.fluendo.com/gstreamer/src/gst-fluendo-mpegdemux

define GST_FLUENDO-MPEGDEMUX_BUILD_CMDS
	$(TARGET_CONFIGURE_OPTS) $(MAKE) -C $(@D) -e
endef

define GST_FLUENDO-MPEGDEMUX_INSTALL_TARGET_CMDS
	$(TARGET_CONFIGURE_OPTS) $(MAKE) -C $(@D) -e DESTDIR=$(TARGET_DIR) install
endef

define GST_FLUENDO_MPEGDEMUX_UNINSTALL_TARGET_CMDS
	$(RM) $(TARGET_DIR)/usr/lib/gstreamer-0.10/libgstdsp.so
endef

GST_FLUENDO_MPEGDEMUX_DEPENDENCIES = gstreamer host-pkgconf

$(eval $(autotools-package))
