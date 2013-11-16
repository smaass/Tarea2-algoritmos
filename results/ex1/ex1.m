load E4M8J;
load E4M10J;
load E4M12J;
load E4M14J;
load E4M16J;

r(1,:) = E4M8J(:,2)./E4M8J(:,1);
r(2,:) = E4M10J(:,2)./E4M10J(:,1);
r(3,:) = E4M12J(:,2)./E4M12J(:,1);
r(4,:) = E4M14J(:,2)./E4M14J(:,1);
r(5,:) = E4M16J(:,2)./E4M16J(:,1);

for i = 1:5
    m(i) = mean(r(i,:));
    s(i) = std(r(i,:));
endfor

x = 8:2:16;

# plot(x, m, '-o')
errorbar(x, m, s)
axis([6 18 1 2])
xlabel ("Total de trabajos");
ylabel ("makespan(online) / makespan(óptimo)");
print -dsvg fig1.svg
