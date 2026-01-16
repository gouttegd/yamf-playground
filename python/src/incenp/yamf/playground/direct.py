from .base import Instrument
from .foo import FooInstrument, Foo
from .bar import BarInstrument, Bar
from .foobar import FooBarInstrument

def read_base(p: str) -> Instrument:
    return Instrument.parse_file(p)

def read_foo(p: str) -> FooInstrument:
    return FooInstrument.parse_file(p)

def read_bar(p: str) -> BarInstrument:
    return BarInstrument.parse_file(p)

def read_foobar(p: str) -> FooBarInstrument:
    return FooBarInstrument.parse_file(p)

def get_foo(ins: Instrument) -> Foo:
    if hasattr(ins, "foo"):
        return Foo.parse_obj(ins.foo)

def get_bar(ins: Instrument) -> Bar:
    if hasattr(ins, "bar"):
        return Bar.parse_obj(ins.bar)
