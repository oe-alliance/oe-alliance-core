# need for dreambox linux-meson64 4.9 and gcc-15
# Under gnu17 a bare "inline" emits no out-of-line copy, leaving calls from
# other files unresolved at module load; -fgnu89-inline emits one again.
export KCFLAGS += " -std=gnu17 -fgnu89-inline \
                    -Wno-error=misleading-indentation \
                    -Wno-error=aggressive-loop-optimizations \
                    -Wno-error=int-to-pointer-cast \
                    -Wno-error=restrict \
                    -Wno-error=int-conversion \
                    -Wno-error=maybe-uninitialized \
                    -Wno-error=discarded-qualifiers \
                    -Wno-error=switch-unreachable \
                    -Wno-error=bool-operation \
                    -Wno-error=declaration-after-statement \
                    -Wno-error=incompatible-pointer-types \
                    -Wno-error=return-mismatch \
                    -Wno-error \
"

PR .= ".1"
# Kbuild dropped the deprecated EXTRA_*FLAGS variables in v6.15, see commit
# e966ad0edd00 ("kbuild: remove EXTRA_*FLAGS support"). They are now silently
# ignored rather than diagnosed, so out-of-tree Makefiles that still use them
# lose their -I flags and fail with "drv_types.h: No such file or directory"
# (drv_types.h is what pulls in drv_conf.h). Rewrite them to the ccflags-y
# family that replaced them back in 2007.
do_configure:append() {
    find "${S}" \( -name Makefile -o -name Kbuild -o -name 'Makefile.*' \) -type f \
        -exec sed -i \
            -e 's/\bEXTRA_CFLAGS\b/ccflags-y/g' \
            -e 's/\bEXTRA_AFLAGS\b/asflags-y/g' \
            -e 's/\bEXTRA_LDFLAGS\b/ldflags-y/g' \
            -e 's/\bEXTRA_CPPFLAGS\b/cppflags-y/g' {} +
}
