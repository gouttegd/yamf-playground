from linkml_runtime.loaders import json_loader

from .pidinst import PIDInstInstrument
from .pidinstfoo import FooInstrument,  Foo

def read_base(p: str) -> PIDInstInstrument:
    return json_loader.load(p, PIDInstInstrument)

def read_foo(p: str) -> FooInstrument:
    return json_loader.load(p, FooInstrument)

def get_foo(ins: PIDInstInstrument) -> Foo:
    if hasattr(ins, "foo"):
        return json_loader.load(ins.foo, Foo)

