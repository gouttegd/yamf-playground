import click

from .base import Instrument
from .foo import FooInstrument
from .bar import BarInstrument
from .foobar import FooBarInstrument

@click.command()
def yamf():
    base_read()
    foo_read()
    bar_read()
    foobar_read()


def base_read():
    # This implementation only supports the base model
    base = Instrument.parse_file("../samples/test-base.json")
    print(f"(Base implementation reading base data): {base.model.modelName}")

    extended = Instrument.parse_file("../samples/test-extended-direct.json")
    print(f"(Base implementation reading extended data): {extended.model.modelName}")

def foo_read():
    # This implementation supports Base+FOO
    base = FooInstrument.parse_file("../samples/test-base.json")
    print(f"(Foo implementation reading base data): {base.model.modelName}")

    extended = FooInstrument.parse_file("../samples/test-extended-direct.json")
    print(f"(Foo implementation reading extended data): {extended.model.modelName}")
    print(f"(Foo implementation reading extended data): {extended.foo.fooName}")

def bar_read():
    # This implementation supports Base+BAR
    base = BarInstrument.parse_file("../samples/test-base.json")
    print(f"(Bar implementation reading base data): {base.model.modelName}")

    extended = BarInstrument.parse_file("../samples/test-extended-direct.json")
    print(f"(Bar implementation reading extended data): {extended.model.modelName}")
    print(f"(Bar implementation reading extended data): {extended.bar.barName}")

def foobar_read():
    # This implementation supports Base+FOO+BAR
    base = FooBarInstrument.parse_file("../samples/test-base.json")
    print(f"(FooBar implementation reading base data): {base.model.modelName}")

    extended = FooBarInstrument.parse_file("../samples/test-extended-direct.json")
    print(f"(FooBar implementation reading extended data): {extended.model.modelName}")
    print(f"(FooBar implementation reading extended data): {extended.foo.fooName}")
    print(f"(FooBar implementation reading extended data): {extended.bar.barName}")