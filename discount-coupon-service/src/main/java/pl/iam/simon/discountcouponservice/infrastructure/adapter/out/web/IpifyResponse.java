package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.web;

public record IpifyResponse(Location location) {

    public record Location(String country) {}
}
