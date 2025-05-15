
do_configure:prepend() {
    sed -i \
        -e 's/^cython.*/cython~=3.1/' \
        ${S}/requirements/cython.txt

    sed -i \
        -e 's/Cython ~= 3.0.12/Cython ~= 3.1/' \
        ${S}/packaging/pep517_backend/_backend.py
}
