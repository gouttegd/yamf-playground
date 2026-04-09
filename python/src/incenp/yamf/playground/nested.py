import json
from typing import Any

from linkml_runtime.loaders import json_loader

from .pidinst import Instrument
from .pidinstfoo import FooInstrument, Foo

def read_base(p: str) -> Instrument:
    return json_loader.load(p, Instrument)

def read_foo(p: str) -> FooInstrument:
    return _read_any(p, FooInstrument, ["com.example.foo"])

def _read_any(p, t, namespaces):
    with open(p, "r") as f:
        raw_map = json.load(f)
    for namespace in namespaces:
        if namespace in raw_map:
            for k, v in raw_map.pop(namespace).items():
                raw_map[k] = v
    return json_loader.load(raw_map, t)

def get_foo(ins: Instrument) -> Foo:
    if hasattr(ins, "com.example.foo"):
        foo_base = getattr(ins, "com.example.foo")
        if "foo" in foo_base:
            return json_loader.load(foo_base["foo"], Foo)
