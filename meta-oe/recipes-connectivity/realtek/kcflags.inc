# need for dreambox linux-meson64 4.9 and gcc-15
export KCFLAGS += " -std=gnu17 \
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
