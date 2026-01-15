from .base import Instrument
from .foo import FooInstrument
from .bar import BarInstrument
from .foobar import FooBarInstrument

def read_base(p: str) -> Instrument:
    return Instrument.parse_file(p)

def read_foo(p: str) -> FooInstrument:
    return FooInstrument.parse_file(p)

def read_bar(p: str) -> BarInstrument:
    return BarInstrument.parse_file(p)

def read_foobar(p: str) -> FooBarInstrument:
    return FooBarInstrument.parse_file(p)
