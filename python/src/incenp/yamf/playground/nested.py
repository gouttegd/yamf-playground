import json
from typing import Any

from .base import Instrument
from .foo import FooInstrument, Foo
from .bar import BarInstrument, Bar
from .foobar import FooBarInstrument

def read_base(p: str) -> Instrument:
    return Instrument.parse_file(p)

def read_foo(p: str) -> FooInstrument:
    return _read_any(p, FooInstrument, ["com.example.foo"])

def read_bar(p: str) -> BarInstrument:
    return _read_any(p, BarInstrument, ["org.example.bar"])

def read_foobar(p: str) -> FooBarInstrument:
    return _read_any(p, FooBarInstrument, ["com.example.foo", "org.example.bar"])

def _read_any(p, t, namespaces):
    with open(p, "r") as f:
        raw_map = json.load(f)
    for namespace in namespaces:
        if namespace in raw_map:
            for k, v in raw_map.pop(namespace).items():
                raw_map[k] = v
    return t.parse_obj(raw_map)

def get_foo(ins: Instrument) -> Foo:
    if hasattr(ins, "com.example.foo"):
        foo_base = getattr(ins, "com.example.foo")
        if "foo" in foo_base:
            return Foo.parse_obj(foo_base["foo"])

def get_bar(ins: Instrument) -> Bar:
    if hasattr(ins, "org.example.bar"):
        bar_base = getattr(ins, "org.example.bar")
        if "bar" in bar_base:
            return Bar.parse_obj(bar_base["bar"])
