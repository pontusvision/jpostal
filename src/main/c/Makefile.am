lib_LTLIBRARIES = libjpostal_parser.la libjpostal_expander.la

libjpostal_expander_la_SOURCES = jpostal_AddressExpander.c
libjpostal_expander_la_CFLAGS = $(LIBPOSTAL_CFLAGS)
libjpostal_expander_la_LIBADD = $(LIBPOSTAL_LIBS)
libjpostal_expander_la_LDFLAGS = -no-undefined -L/c/work/pontus-git/pontusvision-x/tech/pontus-jpostal/src/main/resources/lib/win-amd64/ -lpostal

libjpostal_parser_la_SOURCES = jpostal_AddressParser.c
libjpostal_parser_la_CFLAGS = $(LIBPOSTAL_CFLAGS)
libjpostal_parser_la_LIBADD = $(LIBPOSTAL_LIBS)
libjpostal_parser_la_LDFLAGS = -no-undefined -L/c/work/pontus-git/pontusvision-x/tech/pontus-jpostal/src/main/resources/lib/win-amd64/ -lpostal
