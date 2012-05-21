PREFERRED_VERSION_wpa-supplicant ?= "${@base_version_less_or_equal('DREAMBOX_KERNEL_VERSION', '2.6.18', '0.5.10', '0.7.3', d)}"
