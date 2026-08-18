do_configure:prepend() {
    if grep -q 'iomanip\.h' "${S}/configure.in"; then
        sed -i '/iomanip\.h/d' "${S}/configure.in"
    fi
}
