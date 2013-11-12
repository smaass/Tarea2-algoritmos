load E4M8J;
load E4M10J;
load E4M12J;
load E4M14J;
load E4M16J;

r8 = E4M8J(:,2)./E4M8J(:,1);
r10 = E4M10J(:,2)./E4M10J(:,1);
r12 = E4M12J(:,2)./E4M12J(:,1);
r14 = E4M14J(:,2)./E4M14J(:,1);
r16 = E4M16J(:,2)./E4M16J(:,1);

m(1) = mean(r8);
m(2) = mean(r10);
m(3) = mean(r12);
m(4) = mean(r14);
m(5) = mean(r16);

s(1) = std(r8);
s(2) = std(r10);
s(3) = std(r12);
s(4) = std(r14);
s(5) = std(r16);

x = 8:2:16;

# plot(x, m, '-o')
errorbar(x, m, s)
