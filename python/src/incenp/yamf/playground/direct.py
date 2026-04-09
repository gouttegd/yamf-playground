from linkml_runtime.loaders import json_loader

from .pidinst import Instrument
from .pidinstfoo import FooInstrument,  Foo

def read_base(p: str) -> Instrument:
    return json_loader.load(p, Instrument)

def read_foo(p: str) -> FooInstrument:
    return json_loader.load(p, FooInstrument)

def get_foo(ins: Instrument) -> Foo:
    if hasattr(ins, "foo"):
        return json_loader.load(ins.foo, Foo)

